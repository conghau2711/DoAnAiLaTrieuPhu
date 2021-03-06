<?php

namespace App\Mail;

use Illuminate\Bus\Queueable;
use Illuminate\Mail\Mailable;
use Illuminate\Queue\SerializesModels;
use Illuminate\Contracts\Queue\ShouldQueue;

class DemoMail extends Mailable
{
    use Queueable, SerializesModels;
    public $demo;
    /**
     * Create a new message instance.
     *
     * @return void
     */
    public function __construct($demo)
    {
        $this->demo = $demo;
    }
    /**
     * Build the message.
     *
     * @return $this
     */
    public function build()
    {
        return $this->view('email.demo')
        ->text('email.demo_plain')
        ->attach(public_path('/images').'/demo.jpg',[
            'as'=>'demo.jpg',
            'mime'=>'image/jpeg'
        ]);
    }
}
